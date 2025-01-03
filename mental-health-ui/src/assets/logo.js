const fs = require('fs')
const path = require('path')

// 确保 assets 目录存在
const assetsDir = path.join(__dirname, '../assets')
if (!fs.existsSync(assetsDir)) {
  fs.mkdirSync(assetsDir)
}

// 复制一个示例 logo 文件（你可以之后替换它）
fs.copyFileSync(
  path.join(__dirname, 'logo.png'),
  path.join(assetsDir, 'logo.png')
) 