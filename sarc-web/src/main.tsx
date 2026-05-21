import React, { useEffect, useState } from 'react';
import ReactDOM from 'react-dom/client';
import { initAuth } from './auth/keycloak';
import { AppLayout } from './components/AppLayout';
import { Loading } from './components/Loading';
import { ProtectedRoute } from './components/ProtectedRoute';
import { AdminDashboard } from './pages/AdminDashboard';
import { LoginPage } from './pages/LoginPage';
import { ProfessorDashboard } from './pages/ProfessorDashboard';
import { PublicSchedulePage } from './pages/PublicSchedulePage';
import './styles.css';

function App() {
  const [ready, setReady] = useState(false);
  const [path, setPath] = useState(window.location.pathname);

  useEffect(() => {
    void initAuth().finally(() => setReady(true));

    const onPopState = () => setPath(window.location.pathname);
    window.addEventListener('popstate', onPopState);
    return () => window.removeEventListener('popstate', onPopState);
  }, []);

  function navigate(nextPath: string) {
    window.history.pushState({}, '', nextPath);
    setPath(nextPath);
  }

  if (!ready) {
    return (
      <div className="boot">
        <Loading />
      </div>
    );
  }

  return (
    <AppLayout path={path} navigate={navigate}>
      {renderRoute(path)}
    </AppLayout>
  );
}

function renderRoute(path: string) {
  if (path === '/login') {
    return <LoginPage />;
  }

  if (path === '/professor') {
    return (
      <ProtectedRoute roles={['PROFESSOR', 'ADMIN']}>
        <ProfessorDashboard />
      </ProtectedRoute>
    );
  }

  if (path === '/admin') {
    return (
      <ProtectedRoute roles={['ADMIN']}>
        <AdminDashboard />
      </ProtectedRoute>
    );
  }

  return <PublicSchedulePage />;
}

ReactDOM.createRoot(document.getElementById('root') as HTMLElement).render(
  <React.StrictMode>
    <App />
  </React.StrictMode>
);
