import React from "react";
import "./Card.css";

interface CardProps {
    children: React.ReactNode; 
    id?:string;
    header?:string
}

const Card: React.FC<CardProps> = ({children,id,header}) => {

  return <>
    <div id={id} className="card">
     {header && <div className="card-header">{header}</div>}
       {children}
    </div>
  </>
};
export default Card;