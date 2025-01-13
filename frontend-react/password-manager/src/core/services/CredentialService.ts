import { Credentials } from "../model/Credentials";
import { BaseService } from "./BaseService";

export class CredentialsService extends BaseService {

  private serviceUrl: string = "credentials";

  constructor() {
    super();
  }
  
  async getCredentials(): Promise<Credentials[]> {
    return this.get<Credentials[]>("credentials");
    /*
    return [
      {
        name: "Google",
        accountType: {
          name:"",
          logo: "https://google.com/favicon.ico",
          url: "https://google.com",
        },
        username: "user1@gmail.com",
        password: "secure123",
        vulnerability: "medium",
      },
      {
        name: "Facebook",
        accountType: {
          name:"",
          logo: "https://facebook.com/favicon.ico",
          url: "https://facebook.com",
        },
        username: "user2@gmail.com",
        password: "password123",
        vulnerability: "high",
      },
      {
        name: "Facebook",
        accountType: {
          name:"",
          logo: "https://twitter.com/favicon.ico",
          url: "https://twitter.com",
        },
        username: "user2@gmail.com",
        password: "password123",
        vulnerability: "low",
      },
    ];
    */
  }

  async addCredential(credential: Credentials): Promise<Credentials> {
    return this.post<Credentials, Credentials>(this.serviceUrl, credential);
  }
  
}