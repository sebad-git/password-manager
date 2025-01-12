import React from "react";

import "./SideBar.css"; 

const SideBar: React.FC = () => {

  return <>
    <div className="sidebar">
        <h4>Password Manager</h4>
        <hr/>
        <a href="#dashboard">
            <span className="material-icons">home</span> Dashboard
        </a>
        <a href="#vulnerable-passwords">
            <span className="material-icons">warning</span> Vulnerable Passwords
        </a>
        <a href="#saved-passwords">
            <span className="material-icons">lock</span> Saved Passwords
        </a>
        <a href="#recent-activity">
            <span className="material-icons">history</span> Recent Activity
        </a>
        <a href="#profile">
            <span className="material-icons">person</span> Profile
        </a>
    </div>
  </>
};

export default SideBar;