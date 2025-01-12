import React from "react";
import Card from "../components/card/Card";
import PasswordsTable from "../components/passwords-table/PasswordsTable";
import MainLayout from "../layout/main-content/MainLayout";
import DashBoardCard from "../components/dashboard-card/DashBoardCard";

const HomePage: React.FC = () => {
  return <>
    <MainLayout>
        <br/><br/>
        <div className="row mb-4">
            <div className="col-lg-4">
                <DashBoardCard title="Total Passwords" content="50"/>
            </div>
            <div className="col-lg-4">
                <DashBoardCard title="Recently Added" content="Google"/>
            </div>
            <div className="col-lg-4">
                <DashBoardCard title="Vulnerable Passwords" content="2"/>
            </div>
        </div>

        <Card id="passwords" title="Password List">
            <PasswordsTable/>
        </Card>

        <br/>

    </MainLayout>
  </>;
};

export default HomePage;