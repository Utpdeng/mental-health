const { defineConfig } = require('@vue/cli-service')

module.exports = defineConfig({
  transpileDependencies: true,
  lintOnSave: false,
  devServer: {
    port: 8081,
    proxy: {
      '/api': {
        target: 'http://localhost:8090',
        // target: 'http://172.24.54.8:8090',
        changeOrigin: true,
        secure: false,
        pathRewrite: {
          '^/api': ''
        },
        onProxyReq: (proxyReq, req, res) => {
          if (req.method === 'OPTIONS') {
            proxyReq.setHeader('Access-Control-Allow-Origin', '*')
            proxyReq.setHeader('Access-Control-Allow-Methods', 'GET, POST, PUT, DELETE, OPTIONS')
            proxyReq.setHeader('Access-Control-Allow-Headers', 'Content-Type, Authorization')
          }
        }
      }
    }
  }
})
