import { Grid } from '@mui/material';
import React from "react";
import UnderNavigationBar from "../components/UnderNavigationBar";
import UpperNavigationBar from "../components/UpperNavigationBar";
import "./AccountBookPage.css";
import { useParams } from 'react-router-dom';

const DiaryDetailPage = ()=>{
    const {id} = useParams()
    const upperNavbarName = `${id}일 일기`;


    return(
        <Grid>
            <Grid className="uppernavbar"> 
                <UpperNavigationBar props={upperNavbarName}/>
            </Grid>

            <Grid className="progressive_bar"></Grid>

            <Grid className="body">
                this is DiaryDetailPage
            </Grid>
            
            <Grid className="undernavbar">
                <UnderNavigationBar/>
            </Grid>
        </Grid>
    )
}

export default DiaryDetailPage