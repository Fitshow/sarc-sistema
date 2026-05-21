interface StatusMessageProps {
  type?: 'info' | 'error' | 'success';
  children: React.ReactNode;
}

export function StatusMessage({ type = 'info', children }: StatusMessageProps) {
  return <div className={`status ${type}`}>{children}</div>;
}
