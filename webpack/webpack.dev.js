'use strict';
const utils = require('./vue.utils');
const webpack = require('webpack');
const config = require('../config');
const merge = require('webpack-merge');
const path = require('path');
const baseWebpackConfig = require('./webpack.common');
const FriendlyErrorsPlugin = require('friendly-errors-webpack-plugin');
const portfinder = require('portfinder');
const jhiUtils = require('./utils.js');
const BrowserSyncPlugin = require('browser-sync-webpack-plugin');
const HOST = process.env.HOST;
const PORT = process.env.PORT && Number(process.env.PORT);

module.exports = merge(baseWebpackConfig, {
  mode: 'development',
  module: {
    rules: utils.styleLoaders({ sourceMap: config.dev.cssSourceMap, usePostCSS: true })
  },
  // cheap-module-eval-source-map is faster for development
  devtool: config.dev.devtool,
  entry: {
    global: './src/main/webapp/content/scss/global.scss',
    main: './src/main/webapp/app/main'
  },
  output: {
    path: jhiUtils.root('target/classes/static/'),
    filename: 'app/[name].bundle.js',
    chunkFilename: 'app/[id].chunk.js'
  },
  devServer: {
    contentBase: './target/classes/static/',
    port: 9060,
    proxy: [
      {
        context: [
          '/api',
          '/services',
          '/management',
          '/swagger-resources',
          '/v2/api-docs',
          '/h2-console',
          '/auth'
        ],
        target: 'http://127.0.0.1:8081',
        secure: false,
        headers: { host: 'localhost:9000' }
      },
    ],
    watchOptions: {
      ignored: /node_modules/
    },
    historyApiFallback: true
  },
  plugins: [
    new webpack.DefinePlugin({
      'process.env': require('../config/dev.env')
    }),
    new webpack.HotModuleReplacementPlugin(),
    new webpack.NamedModulesPlugin(), // HMR shows correct file names in console on update.
    new webpack.NoEmitOnErrorsPlugin(),
    new BrowserSyncPlugin(
      {
        host: 'localhost',
        port: 9000,
        proxy: {
          target: 'http://localhost:9060',
        },
        socket: {
          clients: {
            heartbeatTimeout: 60000
          }
        }
        /*
        ,ghostMode: { // uncomment this part to disable BrowserSync ghostMode; https://github.com/jhipster/generator-jhipster/issues/11116
          clicks: false,
          location: false,
          forms: false,
          scroll: false
        } */
      },
      {
        reload: false
      }
    )
  ]
});
