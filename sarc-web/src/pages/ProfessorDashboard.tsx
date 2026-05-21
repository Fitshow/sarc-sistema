import { FormEvent, useEffect, useState } from 'react';
import { keycloak } from '../auth/keycloak';
import { AllocationCards } from '../components/AllocationCards';
import { Loading } from '../components/Loading';
import { StatusMessage } from '../components/StatusMessage';
import { api } from '../services/api';
import type { Allocation, ResourceFilter } from '../types';

const initialForm = {
  disciplina: '',
  data: '',
  horarioInicio: '',
  horarioFim: '',
  recursoId: ''
};

export function ProfessorDashboard() {
  const [allocations, setAllocations] = useState<Allocation[]>([]);
  const [resources, setResources] = useState<ResourceFilter[]>([]);
  const [form, setForm] = useState(initialForm);
  const [loading, setLoading] = useState(true);
  const [message, setMessage] = useState('');
  const [error, setError] = useState('');

  async function load() {
    setLoading(true);
    setError('');
    try {
      const [mine, availableResources] = await Promise.all([
        api.allocations.mine(),
        api.schedules.resources()
      ]);
      setAllocations(mine);
      setResources(availableResources);
    } catch (err) {
      setError(err instanceof Error ? err.message : 'Nao foi possivel carregar sua area.');
    } finally {
      setLoading(false);
    }
  }

  useEffect(() => {
    void load();
  }, []);

  async function createAllocation(event: FormEvent) {
    event.preventDefault();
    setError('');
    setMessage('');

    try {
      await api.allocations.create({
        disciplina: form.disciplina,
        data: form.data,
        horarioInicio: form.horarioInicio,
        horarioFim: form.horarioFim,
        recursoIds: [Number(form.recursoId)]
      });
      setForm(initialForm);
      setMessage('Alocacao criada com sucesso.');
      await load();
    } catch (err) {
      const text = err instanceof Error ? err.message : 'Nao foi possivel criar a alocacao.';
      if (text.toLowerCase().includes('sobreposto') || text.toLowerCase().includes('conflito')) {
        setError('Conflito de horario: o recurso ja esta reservado nesse periodo.');
      } else if (text.toLowerCase().includes('inativo')) {
        setError('Recurso inativo nao pode ser alocado.');
      } else {
        setError(text);
      }
    }
  }

  return (
    <section className="stack">
      <div className="page-title">
        <p className="eyebrow">Area do Professor</p>
        <h1>Minhas alocacoes</h1>
        <p>{keycloak.tokenParsed?.name ?? keycloak.tokenParsed?.preferred_username ?? 'Usuario autenticado'}</p>
      </div>

      {message && <StatusMessage type="success">{message}</StatusMessage>}
      {error && <StatusMessage type="error">{error}</StatusMessage>}

      <div className="two-columns">
        <form className="panel stack" onSubmit={createAllocation}>
          <h2>Criar alocacao</h2>
          <label>
            Disciplina
            <input required value={form.disciplina} onChange={(event) => setForm({ ...form, disciplina: event.target.value })} />
          </label>
          <label>
            Data
            <input required type="date" value={form.data} onChange={(event) => setForm({ ...form, data: event.target.value })} />
          </label>
          <div className="inline-fields">
            <label>
              Inicio
              <input required type="time" value={form.horarioInicio} onChange={(event) => setForm({ ...form, horarioInicio: event.target.value })} />
            </label>
            <label>
              Fim
              <input required type="time" value={form.horarioFim} onChange={(event) => setForm({ ...form, horarioFim: event.target.value })} />
            </label>
          </div>
          <label>
            Recurso disponivel
            <select required value={form.recursoId} onChange={(event) => setForm({ ...form, recursoId: event.target.value })}>
              <option value="">Selecione</option>
              {resources.map((resource) => (
                <option key={resource.id} value={resource.id}>{resource.nome} - {resource.tipoRecurso}</option>
              ))}
            </select>
          </label>
          <p className="hint">A disponibilidade e confirmada ao salvar. Conflitos de horario e recursos inativos serao avisados.</p>
          <button className="primary" type="submit">Criar alocacao</button>
        </form>

        <div className="panel stack">
          <h2>Disponibilidade para consulta</h2>
          <p className="hint">Recursos ativos disponiveis para selecao publica e alocacao.</p>
          <div className="resource-list">
            {resources.map((resource) => (
              <span key={resource.id}>{resource.nome}</span>
            ))}
          </div>
        </div>
      </div>

      {loading ? <Loading /> : <AllocationCards items={allocations} />}
    </section>
  );
}
