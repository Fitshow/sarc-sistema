import Keycloak, { KeycloakInstance } from 'keycloak-js';

const keycloakConfig = {
  url: import.meta.env.VITE_KEYCLOAK_URL ?? 'http://localhost:8090',
  realm: import.meta.env.VITE_KEYCLOAK_REALM ?? 'sarc',
  clientId: import.meta.env.VITE_KEYCLOAK_CLIENT_ID ?? 'sarc-web'
};

export const keycloak: KeycloakInstance = new Keycloak(keycloakConfig);

let initialized = false;

export async function initAuth() {
  if (initialized) {
    return keycloak.authenticated ?? false;
  }

  const authenticated = await keycloak.init({
    onLoad: 'check-sso',
    pkceMethod: 'S256',
    checkLoginIframe: false,
    silentCheckSsoRedirectUri: `${window.location.origin}/silent-check-sso.html`
  });

  initialized = true;
  return authenticated;
}

export async function login() {
  await keycloak.login({ redirectUri: `${window.location.origin}/professor` });
}

export async function logout() {
  await keycloak.logout({ redirectUri: window.location.origin });
}

export async function getToken() {
  if (!keycloak.authenticated) {
    return undefined;
  }

  try {
    await keycloak.updateToken(30);
  } catch {
    await logout();
    return undefined;
  }

  return keycloak.token;
}

export function hasRole(role: 'PROFESSOR' | 'ADMIN') {
  return keycloak.hasRealmRole(role);
}

export function isAuthenticated() {
  return keycloak.authenticated === true;
}
