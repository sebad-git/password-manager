import React from "react";
import "./MainLayout.css";
import NavBar from "../navbar/NavBar";
import SideBar from "../../components/sidebar/SideBar";

interface MainProps {
    children: React.ReactNode;
}

const MainLayout: React.FC<MainProps> = ({children}) => {

  return <>
    <SideBar/>
    <div id="main" className="main-content">
     <NavBar/>
     {children}
    </div>
  </>
};
export default MainLayout;