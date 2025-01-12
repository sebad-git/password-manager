import { useState } from "react";
import { Credentials } from "../model/Credentials";
import { CredentialsService } from "../services/CredentialService";

const credentialsService = new CredentialsService();

export const useCredentialState = () => {
  const [data, setData] = useState<Credentials[]>([]);
  const [globalLoading, setGlobalLoading] = useState<boolean>(false);

  const getCredentials = async () => {
    setGlobalLoading(true);
    try {
      const credentials = await credentialsService.getCredentials();
      setData(credentials);
    } catch (error) {
      console.error("Error fetching credentials:", error);
    } finally {
      setGlobalLoading(false);
      console.log(globalLoading);
    }
  };

  // Add a new credential
  const addCredential = async (credential: Credentials) => {
    try {
      const newCredential = { ...credential, loading: true };
      setData((prevData) => [...prevData, newCredential]);

      const savedCredential = await credentialsService.addCredential(newCredential);
      setData((prevData) =>
        prevData.map((cred) =>
          cred.name === newCredential.name ? { ...savedCredential, loading: false } : cred
        )
      );
    } catch (error) {
      console.error("Error adding credential:", error);
    }
  };

  const showCredential = async (credential:Credentials) => {
    setGlobalLoading(true);
    try {
      const credentials = await credentialsService.showCredential(credential);
      setData(credentials);
    } catch (error) {
      console.error("Error fetching credentials:", error);
    } finally {
      setGlobalLoading(false);
    }
  };

  return {
    data,
    globalLoading,
    getCredentials,
    addCredential,
    showCredential
  };
};