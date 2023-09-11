import { Grid } from '@mui/material';
import React from "react";
import UnderNavigationBar from "../components/UnderNavigationBar";
import UpperNavigationBar from "../components/UpperNavigationBar";
import "./FutureExpenditurePage.css";
import { useSelector } from 'react-redux/es/hooks/useSelector';

const FutureExpenditurePage = ()=>{
    const selectedYearAndMonth = useSelector((state) => state.auth.selectedYearAndMonth);
    const upperNavbarName = `${selectedYearAndMonth} 미래소비`;

    return(
        <Grid>
            <Grid className="uppernavbar">
                <UpperNavigationBar props={upperNavbarName}/>
            </Grid>

            <Grid className='progressive_bar'/>

            <Grid className="body">
                this is FutureExpenditurePage
            </Grid>
            
            <Grid className="undernavbar">
                <UnderNavigationBar/>
            </Grid>
        </Grid>
    )
}

export default FutureExpenditurePage