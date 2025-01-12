import {AccountType} from './AccountType'

export class Credentials {
  name: string;
  accountType: AccountType;
  username: string;
  password: string;
  vulnerability: "low" | "medium" | "high";

  constructor(
    name: string,
    accountType: AccountType,
    username: string,
    password: string,
    vulnerability: "low" | "medium" | "high"
  ) {
    this.name = name;
    this.accountType = accountType;
    this.username = username;
    this.password = password;
    this.vulnerability = vulnerability;
  }
}