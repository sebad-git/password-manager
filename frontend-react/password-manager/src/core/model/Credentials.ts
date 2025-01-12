import {Site} from './Site'

export class Credentials {
  name: string;
  site: Site;
  username: string;
  password: string;
  vulnerability: "low" | "medium" | "high";

  constructor(
    name: string,
    site: Site,
    username: string,
    password: string,
    vulnerability: "low" | "medium" | "high"
  ) {
    this.name = name;
    this.site = site;
    this.username = username;
    this.password = password;
    this.vulnerability = vulnerability;
  }
}