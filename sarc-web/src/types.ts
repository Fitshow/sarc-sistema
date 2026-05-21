export type TipoRecurso = 'SALA' | 'LABORATORIO' | 'COMPUTADOR' | 'PROJETOR' | 'EQUIPAMENTO';
export type PerfilUsuario = 'PROFESSOR' | 'ADMIN';

export interface ScheduleItem {
  disciplina: string;
  professor: string;
  data: string;
  horarioInicio: string;
  horarioFim: string;
  recurso: string;
  tipoRecurso: TipoRecurso;
  localizacao?: string | null;
}

export interface ProfessorFilter {
  id: number;
  nome: string;
}

export interface ResourceFilter {
  id: number;
  nome: string;
  tipoRecurso: TipoRecurso;
  localizacao?: string | null;
}

export interface AllocationResource {
  id: number;
  nome: string;
  tipo: TipoRecurso;
  localizacao?: string | null;
}

export interface Allocation {
  id: number;
  professorId: number;
  professor: string;
  disciplina: string;
  data: string;
  horarioInicio: string;
  horarioFim: string;
  recursos: AllocationResource[];
}

export interface Resource {
  id: number;
  nome: string;
  tipo: TipoRecurso;
  numeroSala?: string | null;
  localizacao?: string | null;
  ativo: boolean;
}

export interface AllocationRequest {
  professorId?: number;
  disciplina: string;
  data: string;
  horarioInicio: string;
  horarioFim: string;
  recursoIds: number[];
}

export interface ResourceRequest {
  nome: string;
  tipo: TipoRecurso;
  numeroSala?: string;
  localizacao?: string;
  ativo?: boolean;
}

export interface ApiError {
  message?: string;
  error?: string;
  fieldErrors?: Record<string, string>;
}
