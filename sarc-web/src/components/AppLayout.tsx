import { ReactNode } from 'react';
import { hasRole, isAuthenticated, logout } from '../auth/keycloak';

interface AppLayoutProps {
  children: ReactNode;
  path: string;
  navigate: (path: string) => void;
}

export function AppLayout({ children, path, navigate }: AppLayoutProps) {
  const authenticated = isAuthenticated();
  const canProfessor = authenticated && (hasRole('PROFESSOR') || hasRole('ADMIN'));
  const canAdmin = authenticated && hasRole('ADMIN');

  return (
    <div className="page">
      <header className="topbar">
        <button className="brand" onClick={() => navigate('/')}>
          <span>SARC</span>
          <small>Grade institucional</small>
        </button>
        <nav className="nav" aria-label="Menu principal">
          <button className={path === '/' ? 'active' : ''} onClick={() => navigate('/')}>
            Consulta Publica
          </button>
          {!authenticated && (
            <button className={path === '/login' ? 'active' : ''} onClick={() => navigate('/login')}>
              Login
            </button>
          )}
          {canProfessor && (
            <button className={path === '/professor' ? 'active' : ''} onClick={() => navigate('/professor')}>
              Area do Professor
            </button>
          )}
          {canAdmin && (
            <button className={path === '/admin' ? 'active' : ''} onClick={() => navigate('/admin')}>
              Administracao
            </button>
          )}
          {authenticated && (
            <button onClick={() => void logout()}>
              Sair
            </button>
          )}
        </nav>
      </header>
      <main className="content">{children}</main>
    </div>
  );
}
