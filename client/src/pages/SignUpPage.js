import { Grid } from "@mui/material";
import React from "react";
import UpperNavigationBar from "../components/UpperNavigationBar";


const SignUpPage = ()=> {
    const upperNavbarName = "회원가입";

    return(
        <Grid>
            <Grid>
                <UpperNavigationBar props = {upperNavbarName}/>
            </Grid>
            <Grid className="progressive_bar">

            </Grid>

        </Grid>
    )
}

export default SignUpPage