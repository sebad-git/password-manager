import axios, { AxiosError, AxiosInstance, AxiosResponse } from "axios";

const BASE_URL:string = "/api";
const JSON_HEADERS:object= {"Content-Type": "application/json"};
const FORM_HEADERS:object= {"Content-Type": "application/x-www-form-urlencoded"};

export class BaseService {
  
  private axiosInstance: AxiosInstance;
  
    constructor() {
      this.axiosInstance = axios.create({
        baseURL: BASE_URL,
        headers: JSON_HEADERS,
        withCredentials: true
      });
    }
  
    async get<T>(endpoint: string): Promise<T> {
      try {
        const response: AxiosResponse<T> = await this.axiosInstance.get(endpoint);
        return response.data;
      } catch (error) {
        this.handleError(error as AxiosError,`GET ${endpoint}`);
      }
    }

    async post<T,U>(endpoint: string, body: T): Promise<U> {
      try {
        const response: AxiosResponse<U> = await this.axiosInstance.post(endpoint, body);
        return response.data;
      } catch (error) {
        this.handleError(error as AxiosError,`POST ${endpoint}`);
      }
    }

    async axiosLogin(body: FormData): Promise<number> {
      const loginEndPoint = "/login";
      try {
        const loginAxios = axios.create({
          baseURL: BASE_URL,
          headers: FORM_HEADERS
        });
        const response: AxiosResponse<string> = await loginAxios.post(loginEndPoint, body);
        return response.status;
      } catch (error) {
        this.handleError(error as AxiosError,`POST ${loginEndPoint}`);
      }
    }
  
    private handleError(error: AxiosError, operation: string): never {
      const status = error.response?.status || "Unknown status";
      const message = error.response?.data || error.message || "An error occurred";
      console.error(`${operation} failed (Status: ${status}): ${JSON.stringify(message)}`);
      throw new Error(JSON.stringify(message));
    }
    
  }