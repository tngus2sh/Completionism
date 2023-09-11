import { Grid } from '@mui/material';
import React from "react";
import UnderNavigationBar from "../components/UnderNavigationBar";
import UpperNavigationBar from "../components/UpperNavigationBar";
import { useSelector } from 'react-redux/es/hooks/useSelector';
import "./FixedExpenditurePage.css";

const FixedExpenditurePage = ()=>{
    const selectedYearAndMonth = useSelector((state) => state.auth.selectedYearAndMonth);
    const upperNavbarName = `${selectedYearAndMonth} 고정지출`;
    return(
        <Grid>
            
            <Grid className="uppernavbar">
                <UpperNavigationBar props={upperNavbarName}/>
                
            </Grid>

            <Grid className='progressive_bar'/>

            <Grid className="body">
                this is FixedExpenditurePage
            </Grid>
            
            <Grid className="undernavbar">
                <UnderNavigationBar/>
            </Grid>
        </Grid>
    )
}

export default FixedExpenditurePage