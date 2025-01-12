
export class BaseService {
  
    private baseUrl: string;
  
    constructor() {
      this.baseUrl = "/api";
    }
  
    async get<T>(endpoint: string): Promise<T> {
      const response = await fetch(`${this.baseUrl}/${endpoint}`);
      if (!response.ok) {
        throw new Error(`GET ${endpoint} failed: ${response.statusText}`);
      }
      return response.json();
    }
  
    async post<T, U>(endpoint: string, body: T): Promise<U> {
      const response = await fetch(`${this.baseUrl}/${endpoint}`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(body),
      });
      if (!response.ok) {
        throw new Error(`POST ${endpoint} failed: ${response.statusText}`);
      }
      return response.json();
    }
    
  }