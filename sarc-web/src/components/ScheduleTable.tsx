import type { ScheduleItem } from '../types';

interface ScheduleTableProps {
  items: ScheduleItem[];
}

export function ScheduleTable({ items }: ScheduleTableProps) {
  if (items.length === 0) {
    return <div className="empty">Nenhum resultado encontrado para os filtros informados.</div>;
  }

  return (
    <div className="table-wrap">
      <table>
        <thead>
          <tr>
            <th>Data</th>
            <th>Horario</th>
            <th>Disciplina</th>
            <th>Professor</th>
            <th>Recurso</th>
            <th>Tipo</th>
            <th>Localizacao</th>
          </tr>
        </thead>
        <tbody>
          {items.map((item, index) => (
            <tr key={`${item.data}-${item.horarioInicio}-${item.recurso}-${index}`}>
              <td>{formatDate(item.data)}</td>
              <td>{item.horarioInicio} - {item.horarioFim}</td>
              <td>{item.disciplina}</td>
              <td>{item.professor}</td>
              <td>{item.recurso}</td>
              <td>{item.tipoRecurso}</td>
              <td>{item.localizacao ?? '-'}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

function formatDate(value: string) {
  const [year, month, day] = value.split('-');
  return `${day}/${month}/${year}`;
}
