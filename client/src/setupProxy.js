// src/setupProxy.js

const { createProxyMiddleware } = require('http-proxy-middleware');

module.exports = function(app) {
  app.use(
    '/api', // 프록시할 요청 경로
    createProxyMiddleware({
      target: 'http://localhost:8080', // 프록시 서버 주소
      changeOrigin: true, // 요청 헤더의 호스트를 변경
    })
  );
};
