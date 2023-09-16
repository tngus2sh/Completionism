import { Grid } from "@mui/material";
import React from "react";
import "./UpperNavigationBar.css";

const UpperNavigationBar = (props) => {
  const upperNavbarName = props.props;

  return (
    <Grid className="uppernavbar" sx={{ backgroundColor: "#F0F1F4" }}>
      <span>
        <strong>{upperNavbarName}</strong>
      </span>
    </Grid>
  );
};

export default UpperNavigationBar;
