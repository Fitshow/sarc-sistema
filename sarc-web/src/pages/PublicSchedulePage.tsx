import { FormEvent, useEffect, useState } from 'react';
import { Loading } from '../components/Loading';
import { ScheduleTable } from '../components/ScheduleTable';
import { StatusMessage } from '../components/StatusMessage';
import { api } from '../services/api';
import type { ProfessorFilter, ResourceFilter, ScheduleItem } from '../types';

export function PublicSchedulePage() {
  const [items, setItems] = useState<ScheduleItem[]>([]);
  const [professors, setProfessors] = useState<ProfessorFilter[]>([]);
  const [resources, setResources] = useState<ResourceFilter[]>([]);
  const [filters, setFilters] = useState({ data: '', professor: '', disciplina: '', recurso: '' });
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  async function loadSchedules(currentFilters = filters) {
    setLoading(true);
    setError('');
    try {
      const data = await api.schedules.list(currentFilters);
      setItems(data);
    } catch (err) {
      setError(err instanceof Error ? err.message : 'Nao foi possivel carregar a grade.');
    } finally {
      setLoading(false);
    }
  }

  useEffect(() => {
    void Promise.all([
      api.schedules.professors().then(setProfessors),
      api.schedules.resources().then(setResources),
      loadSchedules()
    ]).catch(() => setError('Nao foi possivel carregar os filtros publicos.'));
  }, []);

  function submit(event: FormEvent) {
    event.preventDefault();
    void loadSchedules(filters);
  }

  function clear() {
    const empty = { data: '', professor: '', disciplina: '', recurso: '' };
    setFilters(empty);
    void loadSchedules(empty);
  }

  return (
    <section className="stack">
      <div className="page-title">
        <p className="eyebrow">Consulta Publica de Grades</p>
        <h1>Alocacoes de salas, laboratorios e recursos</h1>
        <p>Consulte a grade publica sem login. Visitantes acessam apenas estas informacoes.</p>
      </div>

      <form className="filters panel" onSubmit={submit}>
        <label>
          Data
          <input type="date" value={filters.data} onChange={(event) => setFilters({ ...filters, data: event.target.value })} />
        </label>
        <label>
          Professor
          <select value={filters.professor} onChange={(event) => setFilters({ ...filters, professor: event.target.value })}>
            <option value="">Todos</option>
            {professors.map((professor) => (
              <option key={professor.id} value={professor.id}>{professor.nome}</option>
            ))}
          </select>
        </label>
        <label>
          Disciplina
          <input value={filters.disciplina} onChange={(event) => setFilters({ ...filters, disciplina: event.target.value })} placeholder="Ex.: Software" />
        </label>
        <label>
          Recurso
          <select value={filters.recurso} onChange={(event) => setFilters({ ...filters, recurso: event.target.value })}>
            <option value="">Todos</option>
            {resources.map((resource) => (
              <option key={resource.id} value={resource.id}>{resource.nome}</option>
            ))}
          </select>
        </label>
        <div className="form-actions">
          <button className="primary" type="submit">Filtrar</button>
          <button type="button" onClick={clear}>Limpar</button>
        </div>
      </form>

      {error && <StatusMessage type="error">{error}</StatusMessage>}
      {loading ? <Loading /> : <ScheduleTable items={items} />}
    </section>
  );
}
