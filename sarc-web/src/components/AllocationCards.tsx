import type { Allocation } from '../types';

interface AllocationCardsProps {
  items: Allocation[];
}

export function AllocationCards({ items }: AllocationCardsProps) {
  if (items.length === 0) {
    return <div className="empty">Voce ainda nao possui alocacoes cadastradas.</div>;
  }

  return (
    <div className="grid cards">
      {items.map((item) => (
        <article className="card" key={item.id}>
          <div className="card-head">
            <h3>{item.disciplina}</h3>
            <span>#{item.id}</span>
          </div>
          <p>{item.data} | {item.horarioInicio} - {item.horarioFim}</p>
          <p><strong>Professor:</strong> {item.professor}</p>
          <p><strong>Recursos:</strong> {item.recursos.map((resource) => resource.nome).join(', ')}</p>
        </article>
      ))}
    </div>
  );
}
