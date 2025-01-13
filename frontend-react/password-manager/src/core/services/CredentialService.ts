import { Credentials } from "../model/Credentials";
import { BaseService } from "./BaseService";

export class CredentialsService extends BaseService {

  private serviceUrl: string = "credentials";

  constructor() {
    super();
  }
  
  async getCredentials(): Promise<Credentials[]> {
    return this.get<Credentials[]>("credentials");
  }

  async addCredential(credential: Credentials): Promise<Credentials> {
    return this.post<Credentials, Credentials>(this.serviceUrl, credential);
  }
  
}