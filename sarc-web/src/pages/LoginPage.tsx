import { login } from '../auth/keycloak';

export function LoginPage() {
  return (
    <section className="panel narrow">
      <p className="eyebrow">Acesso institucional</p>
      <h1>Login</h1>
      <p>Entre com sua conta de professor ou administrador. Visitantes publicos devem usar a consulta publica.</p>
      <button className="primary" onClick={() => void login()}>
        Entrar com Keycloak
      </button>
    </section>
  );
}
