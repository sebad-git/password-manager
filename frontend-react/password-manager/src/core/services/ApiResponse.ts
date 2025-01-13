export interface ApiResponse<T> {
  headers:object,
  body: T;
  statusCodeValue: number;
  statusCode: string;
}