import React from "react";
import "./Main.css";
import NavBar from "../navbar/NavBar";

interface MainProps {
    children: React.ReactNode; 
    id?:string;
}

const Card: React.FC<MainProps> = ({children,id}) => {

  return <>
    <div id={id} className="main-content">
     <NavBar/>
     {children}
    </div>
  </>
};
export default Card;