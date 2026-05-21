import { hasRole, isAuthenticated, login } from '../auth/keycloak';
import { StatusMessage } from './StatusMessage';

interface ProtectedRouteProps {
  roles: Array<'PROFESSOR' | 'ADMIN'>;
  children: React.ReactNode;
}

export function ProtectedRoute({ roles, children }: ProtectedRouteProps) {
  if (!isAuthenticated()) {
    return (
      <section className="panel narrow">
        <h1>Acesso restrito</h1>
        <p>Entre com uma conta de professor ou administrador para continuar.</p>
        <button className="primary" onClick={() => void login()}>
          Entrar com Keycloak
        </button>
      </section>
    );
  }

  const allowed = roles.some((role) => hasRole(role));

  if (!allowed) {
    return (
      <StatusMessage type="error">
        Seu usuario nao possui permissao para acessar esta area. Visitantes publicos devem usar a consulta publica.
      </StatusMessage>
    );
  }

  return <>{children}</>;
}
