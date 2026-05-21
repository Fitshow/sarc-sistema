import { FormEvent, useEffect, useState } from 'react';
import { Loading } from '../components/Loading';
import { ScheduleTable } from '../components/ScheduleTable';
import { StatusMessage } from '../components/StatusMessage';
import { api } from '../services/api';
import type { Resource, ScheduleItem, TipoRecurso } from '../types';

const resourceTypes: TipoRecurso[] = ['SALA', 'LABORATORIO', 'COMPUTADOR', 'PROJETOR', 'EQUIPAMENTO'];

const emptyResource = {
  nome: '',
  tipo: 'SALA' as TipoRecurso,
  numeroSala: '',
  localizacao: '',
  ativo: true
};

export function AdminDashboard() {
  const [resources, setResources] = useState<Resource[]>([]);
  const [schedule, setSchedule] = useState<ScheduleItem[]>([]);
  const [form, setForm] = useState(emptyResource);
  const [editingId, setEditingId] = useState<number | null>(null);
  const [allocationIdToRemove, setAllocationIdToRemove] = useState('');
  const [loading, setLoading] = useState(true);
  const [message, setMessage] = useState('');
  const [error, setError] = useState('');

  async function load() {
    setLoading(true);
    setError('');
    try {
      const [allResources, publicSchedule] = await Promise.all([
        api.resources.list(),
        api.schedules.list({})
      ]);
      setResources(allResources);
      setSchedule(publicSchedule);
    } catch (err) {
      setError(err instanceof Error ? err.message : 'Nao foi possivel carregar a administracao.');
    } finally {
      setLoading(false);
    }
  }

  useEffect(() => {
    void load();
  }, []);

  async function saveResource(event: FormEvent) {
    event.preventDefault();
    setError('');
    setMessage('');

    try {
      if (editingId) {
        await api.resources.update(editingId, form);
        setMessage('Recurso atualizado com sucesso.');
      } else {
        await api.resources.create(form);
        setMessage('Recurso criado com sucesso.');
      }

      setEditingId(null);
      setForm(emptyResource);
      await load();
    } catch (err) {
      setError(err instanceof Error ? err.message : 'Nao foi possivel salvar o recurso.');
    }
  }

  function edit(resource: Resource) {
    setEditingId(resource.id);
    setForm({
      nome: resource.nome,
      tipo: resource.tipo,
      numeroSala: resource.numeroSala ?? '',
      localizacao: resource.localizacao ?? '',
      ativo: resource.ativo
    });
  }

  async function toggle(resource: Resource) {
    setError('');
    setMessage('');
    try {
      if (resource.ativo) {
        await api.resources.deactivate(resource.id);
        setMessage('Recurso desativado.');
      } else {
        await api.resources.activate(resource.id);
        setMessage('Recurso ativado.');
      }
      await load();
    } catch (err) {
      setError(err instanceof Error ? err.message : 'Nao foi possivel alterar o status do recurso.');
    }
  }

  async function removeAllocation(event: FormEvent) {
    event.preventDefault();
    setError('');
    setMessage('');

    try {
      await api.allocations.remove(Number(allocationIdToRemove));
      setAllocationIdToRemove('');
      setMessage('Alocacao removida com sucesso.');
      await load();
    } catch (err) {
      setError(err instanceof Error ? err.message : 'Nao foi possivel remover a alocacao.');
    }
  }

  return (
    <section className="stack">
      <div className="page-title">
        <p className="eyebrow">Administracao</p>
        <h1>Recursos e alocacoes</h1>
        <p>Gerencie recursos institucionais e acompanhe a grade publica consolidada.</p>
      </div>

      {message && <StatusMessage type="success">{message}</StatusMessage>}
      {error && <StatusMessage type="error">{error}</StatusMessage>}

      <div className="two-columns">
        <form className="panel stack" onSubmit={saveResource}>
          <h2>{editingId ? 'Editar recurso' : 'Criar recurso'}</h2>
          <label>
            Nome
            <input required value={form.nome} onChange={(event) => setForm({ ...form, nome: event.target.value })} />
          </label>
          <label>
            Tipo
            <select value={form.tipo} onChange={(event) => setForm({ ...form, tipo: event.target.value as TipoRecurso })}>
              {resourceTypes.map((type) => (
                <option key={type} value={type}>{type}</option>
              ))}
            </select>
          </label>
          <div className="inline-fields">
            <label>
              Numero sala
              <input value={form.numeroSala} onChange={(event) => setForm({ ...form, numeroSala: event.target.value })} />
            </label>
            <label>
              Localizacao
              <input value={form.localizacao} onChange={(event) => setForm({ ...form, localizacao: event.target.value })} />
            </label>
          </div>
          <label className="checkbox">
            <input type="checkbox" checked={form.ativo} onChange={(event) => setForm({ ...form, ativo: event.target.checked })} />
            Ativo
          </label>
          <div className="form-actions">
            <button className="primary" type="submit">Salvar</button>
            {editingId && <button type="button" onClick={() => { setEditingId(null); setForm(emptyResource); }}>Cancelar</button>}
          </div>
        </form>

        <form className="panel stack" onSubmit={removeAllocation}>
          <h2>Remover alocacao</h2>
          <p className="hint">Informe o id da alocacao para remocao administrativa.</p>
          <input required type="number" min="1" value={allocationIdToRemove} onChange={(event) => setAllocationIdToRemove(event.target.value)} placeholder="Id da alocacao" />
          <button className="danger" type="submit">Remover alocacao</button>
        </form>
      </div>

      <section className="panel stack">
        <h2>Recursos cadastrados</h2>
        {loading ? <Loading /> : (
          <div className="table-wrap">
            <table>
              <thead>
                <tr>
                  <th>Nome</th>
                  <th>Tipo</th>
                  <th>Sala</th>
                  <th>Localizacao</th>
                  <th>Status</th>
                  <th>Acoes</th>
                </tr>
              </thead>
              <tbody>
                {resources.map((resource) => (
                  <tr key={resource.id}>
                    <td>{resource.nome}</td>
                    <td>{resource.tipo}</td>
                    <td>{resource.numeroSala ?? '-'}</td>
                    <td>{resource.localizacao ?? '-'}</td>
                    <td>{resource.ativo ? 'Ativo' : 'Inativo'}</td>
                    <td className="row-actions">
                      <button type="button" onClick={() => edit(resource)}>Editar</button>
                      <button type="button" onClick={() => void toggle(resource)}>
                        {resource.ativo ? 'Desativar' : 'Ativar'}
                      </button>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        )}
      </section>

      <section className="panel stack">
        <h2>Grade publica</h2>
        <ScheduleTable items={schedule} />
      </section>
    </section>
  );
}
