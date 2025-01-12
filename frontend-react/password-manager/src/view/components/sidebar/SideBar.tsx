import React from "react";

import "./SideBar.css"; 

const SideBar: React.FC = () => {

  return <>
    <div className="sidebar">
        <a href="/">
            <span className="material-icons">home</span> 
            <h6>Home</h6> 
        </a>
        <a href="#dashboard">
            <span className="material-icons">add</span>
            <h6>Add</h6>
        </a>
        <a href="#vulnerable-passwords">
            <span className="material-icons">warning</span>
            <h6>Vulnerable Passwords</h6>
        </a>
        <a href="#profile">
            <span className="material-icons">person</span>
            <h6>Profile</h6>
        </a>
    </div>
  </>
};

export default SideBar;