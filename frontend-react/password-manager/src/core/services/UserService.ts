import { User } from "../model/User";
import { BaseService } from "./BaseService";

const USER_SERVICE = "users";

export class UserService extends BaseService {
  
  private static instance: UserService | null = null;

  private constructor() { super(); }

  public static getInstance(): UserService {
    if (!this.instance) { this.instance = new UserService(); }
    return this.instance;
  }
  
  async isUserAuthenticated(): Promise<boolean> {
    try {
      const endPoint = `${USER_SERVICE}/isAuthenticated`;
      return await this.get<boolean>(endPoint);
    } catch { return false; }
  }

  async login(username: string, password: string): Promise<number> {
    const loginData = new FormData();
    loginData.set('username', username);
    loginData.set('password', password);
    return await this.axiosLogin(loginData);
  }

  async createUser(user: User): Promise<string> {
    const endPoint = `${USER_SERVICE}/create`;
    return await (this.post<User,string>(endPoint, user));
  }
  
}