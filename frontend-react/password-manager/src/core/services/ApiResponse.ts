export interface ApiResponse<T> {
    status: string;
    body: T;
    message: string | null;
  }