import React, { useState } from "react";
import { Grid, TextField, Button } from "@mui/material";
import axios from "axios";
import UpperNavigationBar from "../components/UpperNavigationBar";

const SignUpPage = () => {
  const [formData, setFormData] = useState({
    username: "",
    password: "",
    name: "",
    email: "",
  });

  const upperNavbarName = "회원가입";

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value,
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      // 회원가입 요청을 보내기 전에 입력 데이터를 확인하고 유효성 검사를 수행할 수 있습니다.
      // 이 예제에서는 간단한 데이터 유효성 검사만 수행합니다.
      if (!formData.username || !formData.password || !formData.name || !formData.email) {
        alert("모든 필드를 채워주세요.");
        return;
      }

      // Axios를 사용하여 회원가입 요청을 보냅니다.
      const response = await axios.post("/api/signup", formData);

      // 회원가입 성공 시 처리할 코드를 여기에 추가할 수 있습니다.

      // 예를 들어, 회원가입 성공 메시지를 출력하거나 다른 페이지로 이동할 수 있습니다.

      alert("회원가입이 성공적으로 완료되었습니다.");
    } catch (error) {
      // 회원가입 실패 시 처리할 코드를 여기에 추가할 수 있습니다.
      alert("회원가입 중 오류가 발생했습니다.");
    }
  };

  return (
    <Grid container spacing={2}>
      <Grid item xs={12}>
        <UpperNavigationBar props={upperNavbarName} />
      </Grid>
      <Grid item xs={12} className="progressive_bar">
        <form onSubmit={handleSubmit}>
          <TextField
            label="아이디"
            name="username"
            fullWidth
            value={formData.username}
            onChange={handleChange}
          />
          <TextField
            label="비밀번호"
            name="password"
            type="password"
            fullWidth
            value={formData.password}
            onChange={handleChange}
          />
          <TextField
            label="이름"
            name="name"
            fullWidth
            value={formData.name}
            onChange={handleChange}
          />
          <TextField
            label="이메일"
            name="email"
            fullWidth
            value={formData.email}
            onChange={handleChange}
          />
          <Button type="submit" variant="contained" color="primary">
            회원가입
          </Button>
        </form>
      </Grid>
    </Grid>
  );
};

export default SignUpPage;
