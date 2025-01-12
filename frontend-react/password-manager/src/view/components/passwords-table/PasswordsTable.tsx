import "./PasswordsTable.css";
import { useCredentialState } from "../../../core/states/useCredentialState";
import { useEffect } from "react";

const PasswordsTable: React.FC = () => {

  const { data, globalLoading, getCredentials, showCredential } = useCredentialState();
  
  useEffect(() => {
    getCredentials();
  }, []);

  return (
    <>
    <div className="row pm-search">
      <div className="col-1 search-label search-item">Search</div>
      <div className="col-6 search-item">
        <input type="text" className="form-control" placeholder="Name" aria-label="Name"/>
      </div>
      {globalLoading && <div className="col"> 
        <div className="spinner-border text-primary" role="status">
          <span className="visually-hidden">Loading...</span> </div> 
      </div>}
    </div>
    <table className="table">
    <thead>
      <tr>
        <th>Service</th>
        <th>Username</th>
        <th>Password</th>
        <th>Decode</th>
        <th>Vulnerability</th>
        <th>Actions</th>
      </tr>
    </thead>
    <tbody className="table-group-divider">
      {data.length === 0 ? (
        <tr>
          <td colSpan={5} style={{ textAlign: "center" }}>
            No credentials available.
          </td>
        </tr>
      ) : (
        data.map((credential, index) => (
          <tr key={index}>
            <td>
              <div style={{ display: "flex", alignItems: "center" }}>
                <img
                  src={credential.accountType.logo || "placeholder-logo.png"}
                  alt={credential.name}
                  style={{ width: 24, height: 24, marginRight: 8 }}
                />
                <a className="pm-site" href={credential.accountType.url} target="_blank" rel="noopener noreferrer">
                  {credential.accountType.name}
                </a>
              </div>
            </td>
            <td>{credential.username}</td>
            <td>{credential.password}</td>
            <td>
              <span className="material-icons action icon-blue" onClick={() => showCredential(credential)}>visibility</span>
            </td>
            <td>
              {credential.vulnerability==="low" && <span className="badge low">{credential.vulnerability}</span>}
              {credential.vulnerability==="medium" && <span className="badge medium">{credential.vulnerability}</span>}
              {credential.vulnerability==="high" && <span className="badge high">{credential.vulnerability}</span>}
            </td>
            <td>
              <div className="row">
                <div className="col">
                    <span className="material-icons action icon-black" onClick={() => showCredential(credential)}>edit</span>
                  </div>
                  <div className="col">
                    <span className="material-icons action icon-red" onClick={() => showCredential(credential)}>delete</span>
                  </div>
              </div>
              
            </td>
          </tr>
        ))
      )}
    </tbody>
  </table>
  </>
  );
};

export default PasswordsTable;
