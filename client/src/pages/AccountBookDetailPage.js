import { Grid } from '@mui/material';
import React from "react";
import UnderNavigationBar from "../components/UnderNavigationBar";
import UpperNavigationBar from "../components/UpperNavigationBar";
import "./AccountBookDetailPage.css";
import { useParams } from 'react-router-dom';

const AccoutBookDetailPage = ()=>{
    const {id} = useParams();
    // console.log ({id})
    const upperNavbarName = id;

    return(
        <Grid>
            <Grid className="uppernavbar"> 
                <UpperNavigationBar props={upperNavbarName}/>
            </Grid>

            <Grid className="progressive_bar"></Grid>

            <Grid className="daily_consumption_plan_box">
            </Grid>

            <Grid className="body">
                this is AccountBookDetailPage
            </Grid>
             
            <Grid className="undernavbar">
                <UnderNavigationBar/>
            </Grid>
        </Grid>
    )
}

export default AccoutBookDetailPage