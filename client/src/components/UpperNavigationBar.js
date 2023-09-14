import React from "react";
import "./UpperNavigationBar.css";

const UpperNavigationBar = (props) => {
    const upperNavbarName = props.props

    return(
        <div className ="uppernavbar">
            {upperNavbarName}
        </div>
    )
}

export default UpperNavigationBar