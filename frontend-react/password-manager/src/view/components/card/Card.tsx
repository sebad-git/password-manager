import React from "react";
import "./Card.css";

interface CardProps {
    id?:string;
    title?:string
    children: React.ReactNode;
}

const Card: React.FC<CardProps> = ({id,title,children}) => {

  return <>
    <div id={id} className="card">
     {title && <div className="card-header">{title}</div>}
       {children}
    </div>
  </>
};
export default Card;