import { Grid } from '@mui/material';
import React, { useState } from "react";
import UnderNavigationBar from "../components/UnderNavigationBar";
import UpperNavigationBar from "../components/UpperNavigationBar";
import "./FutureExpenditurePage.css";
import { useSelector } from 'react-redux/es/hooks/useSelector';
import { useEffect } from 'react';
import axios from 'axios';
import SampleKeyboard from '../components/virtualKeyboard';

const FutureExpenditurePage = () => {
    const selectedYearAndMonth = useSelector((state) => state.auth.selectedYearAndMonth);
    const upperNavbarName = `${selectedYearAndMonth} 미래소비`;

    const [isKeyboardVisible, setKeyboardVisible] = useState(false); // 키보드 가시성 상태

    useEffect(() => {
        loadData();
    }, []);

    const loadData = async () => {
        // ...

    };

    // 키보드 토글 함수
    const toggleKeyboard = () => {
        setKeyboardVisible(!isKeyboardVisible);
    };

    return (
        <Grid>
            <Grid className="uppernavbar">
                <UpperNavigationBar props={upperNavbarName}/>
            </Grid>

            <Grid className='progressive_bar'/>

            <Grid className="body">
                this is FutureExpenditurePage
            </Grid>

            {/* 가상 쿼티 키보드 열기 */}
            <Grid>
                <button onClick={toggleKeyboard}>미래소비 생성하기</button>
            </Grid>

            {/* 키보드 가시성에 따라 렌더링 */}
            {isKeyboardVisible && (
                <SampleKeyboard />
            )}

            <Grid className="undernavbar">
                <UnderNavigationBar/>
            </Grid>
        </Grid>
    );
}

export default FutureExpenditurePage;
