import { Grid } from '@mui/material';
import React from "react";
import "./UpperNavigationBar.css";

const UpperNavigationBar = (props) => {
    const upperNavbarName = props.props

    return(
        <Grid className ="uppernavbar">
            {upperNavbarName}
        </Grid>
    )
}

export default UpperNavigationBar