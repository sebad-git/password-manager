import React from "react";
import "./NavBar.css";
import brandIcon from "../../assets/card.svg";
import userIcon from "../../assets/user.svg"; 

const NavBar: React.FC = () => {

  return <>
  <div className="bar">
  <div className="bar-left">
    <img src={brandIcon} alt="Icon"/>
    <a href="/">Password Manager</a>
  </div>
  <div className="bar-right">
    <img className="user-icon" src={userIcon} alt="Icon" onClick={() => console.log("click")}/>
  </div>
</div>
  </>
};

export default NavBar;