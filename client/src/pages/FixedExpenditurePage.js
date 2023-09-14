import { Grid } from '@mui/material';
import React, { useState } from "react";
import UnderNavigationBar from "../components/UnderNavigationBar";
import UpperNavigationBar from "../components/UpperNavigationBar";
import { useSelector } from 'react-redux/es/hooks/useSelector';
import "./FixedExpenditurePage.css";
import axios from 'axios'; // axios 라이브러리 추가
import { useDispatch } from 'react-redux';
import { fatchPinnedData } from '../redux/authSlice';
import { useEffect } from 'react';

const FixedExpenditurePage = () => {
    const selectedYearAndMonth = useSelector((state) => state.auth.selectedYearAndMonth);
    const upperNavbarName = `${selectedYearAndMonth} 고정지출`;
    const dispatch = useDispatch();
    const [useAxios,setUseAxios] = useState(false);

    // 입력 필드와 연결된 state 변수들
    const [todo, setTodo] = useState("");
    const [cost, setCost] = useState(0);
    const [fixed, setFixed] = useState(true);
    const [plus, setPlus] = useState(false);
    const [periodType, setPeriodType] = useState(false);
    const [period, setPeriod] = useState(5);

    useEffect(() => {
        loadData();
    }, [useAxios]);

    const loadData = async () => {
        // 로컬 스토리지에서 엑세스 토큰 가져오기
        const accessToken = localStorage.getItem('accessToken');

        // Axios 요청 헤더 설정
        const headers = {
            Authorization: `Bearer ${accessToken}`, // 엑세스 토큰을 Bearer 토큰으로 헤더에 추가
        };

        try {
            const response = await axios.get('/api/schedule/pinned', { headers });
            console.log(response.data.dataBody);
            dispatch(fatchPinnedData(response.data.dataBody))

        } catch (error) {
            console.error(error);
        }
    };


    const createData = async () => {
        // 데이터를 객체로 만들기
        const data = {
            todo: todo,
            cost: cost,
            fixed: fixed,
            plus: plus,
            periodType: periodType,
            period: period
        };

        // 로컬 스토리지에서 엑세스 토큰 가져오기 (필요한 경우)
        const accessToken = localStorage.getItem('accessToken');

        // Axios 요청 헤더 설정 (필요한 경우)
        const headers = {
            Authorization: `Bearer ${accessToken}`, // 엑세스 토큰을 Bearer 토큰으로 헤더에 추가
        };

        try {
            const response = await axios.post('/api/schedule/pinned', data, { headers });
            console.log(response);
            setUseAxios(!useAxios)
            // 요청 후 입력 필드 초기화
            setTodo("");
            setCost(0);
            setFixed(true);
            setPlus(false);
            setPeriodType(false);
            setPeriod(5);
        } catch (error) {
            console.error(error);
            console.log(data);
        }
    };

    return (
        <Grid>
            <Grid className="uppernavbar">
                <UpperNavigationBar props={upperNavbarName} />
            </Grid>

            <Grid className='progressive_bar' />

            <Grid className="body">
                this is FixedExpenditurePage
            </Grid>

            <Grid>
                <div>
                    <label>Todo:</label>
                    <input type="text" value={todo} onChange={(e) => setTodo(e.target.value)} />
                </div>
                <div>
                    <label>Cost:</label>
                    <input type="number" value={cost} onChange={(e) => setCost(parseFloat(e.target.value))} />
                </div>
                <div>
                    <label>Fixed:</label>
                    <input type="checkbox" checked={fixed} onChange={(e) => setFixed(e.target.checked)} />
                </div>
                <div>
                    <label>Plus:</label>
                    <input type="checkbox" checked={plus} onChange={(e) => setPlus(e.target.checked)} />
                </div>
                <div>
                    <label>Period Type:</label>
                    <input type="checkbox" checked={periodType} onChange={(e) => setPeriodType(e.target.checked)} />
                </div>
                <div>
                    <label>Period:</label>
                    <input type="number" value={period} onChange={(e) => setPeriod(parseInt(e.target.value))} />
                </div>
                <button onClick={createData}>고정지출 생성하기</button>
                <hr />
            </Grid>

            <Grid className="undernavbar">
                <UnderNavigationBar />
            </Grid>
        </Grid>
    );
}

export default FixedExpenditurePage;
