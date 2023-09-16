import React, { useEffect, useState } from "react";
import Modal from "react-modal";
import UnderNavigationBar from "../components/UnderNavigationBar";
import UpperNavigationBar from "../components/UpperNavigationBar";
import "./AccountBookDetailPage.css";
import { useParams } from "react-router-dom";
import axios from "axios";
import { fatchMonthHistoryData } from "../redux/authSlice";
import { useSelector } from "react-redux";
import { useDispatch } from "react-redux";
import {colors} from "@mui/material";
import {blue} from "@mui/material/colors";
import SmartToyOutlinedIcon from "@mui/icons-material/SmartToyOutlined";

const emotions = [
  "ANGER",
  "JOY",
  "DESIRE",
  "GRATITUDE",
  "GRIEF",
  "DISGUST",
  "FEAR",
  "CURIOSTY",
  "NEUTRAL",
];

// 모달 스타일을 설정합니다.
const modalStyle = {
  content: {
    top: "50%",
    left: "50%",
    right: "auto",
    bottom: "auto",
    marginRight: "-50%",
    transform: "translate(-50%, -50%)",
  },
};

const AccoutBookDetailPage = () => {
  const dispatch = useDispatch();
  const { id } = useParams();
  const upperNavbarName = `${id}일 가계부`;
  const MonthHistoryData = useSelector((state) => state.auth.MonthHistoryData);
  const [selectedItemIndex, setSelectedItemIndex] = useState(-1);
  const [diaryContent, setDiaryContent] = useState("");
  const [selectedEmotion, setSelectedEmotion] = useState("NEUTRAL");
  const [useAxios, setUseAxios] = useState(false);
  const [isModalOpen, setIsModalOpen] = useState(false);

  useEffect(() => {
    loadData();
  }, [useAxios]);

  const loadData = async () => {
    const accessToken = localStorage.getItem("accessToken");
    const headers = {
      Authorization: `Bearer ${accessToken}`,
    };
    try {
      const response = await axios.get(`/api/history?date=${id.slice(0,4)+'-'+id.slice(4,6)+'-'+id.slice(6,9)}`, {
        headers,
      });
      dispatch(fatchMonthHistoryData(response.data.dataBody));
      console.log(id.slice(0,4)+'-'+id.slice(4,6)+'-'+id.slice(6,9))
      console.log(response.data.dataBody);
    } catch (error) {
      console.error(error);
    }
  };

  const createDiary = async (id) => {
    const accessToken = localStorage.getItem("accessToken");
    const data = {
      feel: selectedEmotion,
      diary: diaryContent,
    };
    const headers = {
      Authorization: `Bearer ${accessToken}`,
    };
    try {
      const response = await axios.post(
        `/api/transaction/${id}/diary`,
        data,
        {
          headers,
        }
      );
      console.log(response.data);
      setUseAxios(!useAxios);
      setIsModalOpen(false); // 일기 작성이 완료되면 모달을 닫습니다.
    } catch (error) {
      console.error(error);
    }
  };

  return (
    <div className="account-detail-page">
      <div className="uppernavbar">
        <UpperNavigationBar props={upperNavbarName} />
      </div>

      <div className="progressive_bar"></div>

      <div className="account-detail-page_top_box">
        <div className="main-header-info-text-container ">
          총 소비금액: {}원
          <div style={{ marginBottom: "0.3rem" , marginTop:"0.3rem" }}>
            <SmartToyOutlinedIcon sx={{ fontSize: "2.3rem", color: "#60f14f" }} />
          </div>
          AI의 조언: {}
        </div>
      </div>

      {/*<div className="body">this is AccountBookDetailPage</div>*/}

      <div className="account-detail-manual">
        😀 가계부에 이모지와 함께 한 줄 일기를 작성해보세요! 😉
      </div>


      <div>
        {MonthHistoryData.map((item, index) => {
          return (
            <div key={item.transactionId} className='transaction-item-container'>
              {item.transactionId}|{item.time}|{item.cost}|{item.category}|
              {item.place}|{item.diary}|{item.feel}|
              <button
                onClick={() => {
                  setSelectedItemIndex(index);
                  setIsModalOpen(true); // 모달 열기
                  setDiaryContent("");
                  setSelectedEmotion("NEUTRAL");
                }}
              >
                한줄일기쓰기!
              </button>
              <Modal
                isOpen={isModalOpen}
                onRequestClose={() => setIsModalOpen(false)} // 모달 닫기
                style={modalStyle}
                contentLabel="일기 작성 모달"
              >
                <h2>일기 작성</h2>
                <select
                  value={selectedEmotion}
                  onChange={(e) => setSelectedEmotion(e.target.value)}
                >
                  {emotions.map((emotion) => (
                    <option key={emotion} value={emotion}>
                      {emotion}
                    </option>
                  ))}
                </select>
                <input
                  placeholder="일기를 입력하세요"
                  value={diaryContent}
                  onChange={(e) => setDiaryContent(e.target.value)}
                />
                <button onClick={() => createDiary(item.transactionId)}>
                  작성완료
                </button>
                <button onClick={() => setIsModalOpen(false)}>취소</button>
              </Modal>
            </div>
          );
        })}
        <hr />
      </div>

      <div className="undernavbar">
        <UnderNavigationBar />
      </div>
    </div>
  );
};

export default AccoutBookDetailPage;
