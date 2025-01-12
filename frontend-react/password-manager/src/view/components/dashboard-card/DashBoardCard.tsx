

import React from "react";
import "./DashBoardCard.css";

interface DashBoardCardProps {
    id?:string;
    title: string;
    content:string
}

const DashBoardCard: React.FC<DashBoardCardProps> = ({id,title,content}) => {

  return <>
    <div id={id} className="card">
        <div className="card-body">
            <h5 className="card-title">{title}</h5>
            <p className="card-text" id="card-content">{content}</p>
        </div>
    </div>
  </>
};

export default DashBoardCard;