import { Credentials } from "../model/Credentials";
import { BaseService } from "./BaseService";

export class CredentialsService extends BaseService {

  private serviceUrl: string = "credentials";

  constructor() {
    super();
  }
  
  async getCredentials(): Promise<Credentials[]> {
    //return this.get<Credentials[]>(this.serviceUrl);
    return [
      {
        name: "Google",
        site: {
          name:"",
          logo: "https://google.com/favicon.ico",
          url: "https://google.com",
        },
        username: "user1@gmail.com",
        password: "secure123",
        vulnerability: "low",
      },
      {
        name: "Facebook",
        site: {
          name:"",
          logo: "https://facebook.com/favicon.ico",
          url: "https://facebook.com",
        },
        username: "user2@gmail.com",
        password: "password123",
        vulnerability: "medium",
      },
    ];
  }

  async addCredential(credential: Credentials): Promise<Credentials> {
    return this.post<Credentials, Credentials>(this.serviceUrl, credential);
  }

  async showCredential(credential: Credentials): Promise<Credentials[]> {
    return this.post<Credentials, Credentials[]>(this.serviceUrl, credential);
  }
  
}