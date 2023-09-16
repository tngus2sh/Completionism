import React from "react";
import "./UnderNavigationBar.css";
import { Link } from "react-router-dom";
import HouseRoundedIcon from "@mui/icons-material/HouseRounded";
import LibraryBooksRoundedIcon from "@mui/icons-material/LibraryBooksRounded";
import PsychologyAltRoundedIcon from "@mui/icons-material/PsychologyAltRounded";
import SettingsRoundedIcon from "@mui/icons-material/SettingsRounded";

const UnderNavigationBar = () => {
  return (
    <div className="undernavbar">
      <Link to="/mainpage">
        <div className="footer-link-container">
          <HouseRoundedIcon sx={{ fontSize: "2rem" }} />
          <span>홈</span>
        </div>
      </Link>
      <Link to="/accountbook">
        <div className="footer-link-container">
          <LibraryBooksRoundedIcon sx={{ fontSize: "2rem" }} />
          <span>가계부</span>
        </div>
      </Link>
      <Link to="/diary">
        <div className="footer-link-container">
          <PsychologyAltRoundedIcon sx={{ fontSize: "2rem" }} />
          <span>다이어리</span>
        </div>
      </Link>

      <Link to="/setting">
        <div className="footer-link-container">
          <SettingsRoundedIcon sx={{ fontSize: "2rem" }} />
          <span>설정</span>
        </div>
      </Link>
    </div>
  );
};

export default UnderNavigationBar;
