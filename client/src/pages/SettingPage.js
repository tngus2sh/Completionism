import { Grid } from '@mui/material';
import React from "react";
import UnderNavigationBar from "../components/UnderNavigationBar";
import UpperNavigationBar from "../components/UpperNavigationBar";
import "./SettingPage.css";
import { logoutState } from '../redux/authSlice';
import { useDispatch } from 'react-redux';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

const SettingPage = ()=>{
    const upperNavbarName = "설정";
    const dispatch = useDispatch();
    const navigate = useNavigate();

    const logOut = async (e) => {
        e.preventDefault();

        // 로컬 스토리지에서 엑세스 토큰 가져오기
        const accessToken = localStorage.getItem('accessToken');

        // Axios 요청 헤더 설정
        const headers = {
            Authorization: `Bearer ${accessToken}`, // 엑세스 토큰을 Bearer 토큰으로 헤더에 추가
        };

        try {
            // 로그아웃 요청 보내기
            const response = await axios.get('/api/auth/logout', {headers});

            console.log(response.data);

            // 로그아웃 후 필요한 작업 수행
            navigate('/')
            dispatch(logoutState())

        } catch (error) {
            console.error(error);
            console.log(accessToken)
        }
    };


    return(
        <Grid>
            <Grid className="uppernavbar">
                <UpperNavigationBar/>
            </Grid>
            
            <Grid className='progressive_bar' props={upperNavbarName}/>

            <Grid className="body">
                this is SettingPage
            </Grid>

            <button onClick={logOut}>로그아웃 </button>
            
            <Grid className="undernavbar">
                <UnderNavigationBar/>
            </Grid>
        </Grid>
    )
}

export default SettingPage