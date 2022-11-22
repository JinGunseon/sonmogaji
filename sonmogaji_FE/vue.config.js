const { defineConfig } = require("@vue/cli-service");
module.exports = defineConfig({
  transpileDependencies: true,
  devServer: {
    port: 3000,
    proxy: {
      '/api': {
          // target: 'http://localhost:8080' 
          target: 'https://j7a308.p.ssafy.io' 

      },
      '/room': {
        // target: 'http://localhost:8080' 
        target: 'https://j7a308.p.ssafy.io' 

    }
  }
  }
})
