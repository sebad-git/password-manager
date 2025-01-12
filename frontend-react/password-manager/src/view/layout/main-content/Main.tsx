import React from "react";
import "./Main.css";

interface MainProps {
    children: React.ReactNode; 
    id?:string;
}

const Card: React.FC<MainProps> = ({children,id}) => {

  return <>
    <div id={id} className="main-content">
     {children}
    </div>
  </>
};
export default Card;