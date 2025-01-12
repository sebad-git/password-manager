import React from "react";
import SideBar from "../components/sidebar/SideBar";
import Card from "../components/card/Card";
import PasswordsTable from "../components/passwords-table/PasswordsTable";
import Main from "../layout/main-content/Main";

const HomePage: React.FC = () => {
  return <>
  <SideBar/>
    <Main>
        <br/>
        <div className="row mb-4">
            <div className="col-lg-4">
                <div className="card">
                    <div className="card-body">
                        <h5 className="card-title">Total Passwords</h5>
                        <p className="card-text" id="total-passwords">50</p>
                    </div>
                </div>
            </div>
            <div className="col-lg-4">
                <div className="card">
                    <div className="card-body">
                        <h5 className="card-title">Recently Added</h5>
                        <p className="card-text" id="recent-password">example.com</p>
                    </div>
                </div>
            </div>
            <div className="col-lg-4">
                <div className="card">
                    <div className="card-body">
                        <h5 className="card-title">Recently Retrieved</h5>
                        <p className="card-text" id="recent-retrieved">another.com</p>
                    </div>
                </div>
            </div>
        </div>

        <Card id="passwords" header="Password List">
            <PasswordsTable/>
        </Card>

        <br/>

    </Main>
  </>;
};

export default HomePage;