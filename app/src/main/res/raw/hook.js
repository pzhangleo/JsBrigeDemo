const TronWeb = require('tronweb')

// This provider is optional, you can just use a url for the nodes instead
const HttpProvider = TronWeb.providers.HttpProvider;
const fullNode = new HttpProvider('https://api.trongrid.io'); // Full node http endpoint
const solidityNode = new HttpProvider('https://api.trongrid.io'); // Solidity node http endpoint
const eventServer = new HttpProvider('https://api.trongrid.io'); // Contract events http endpoint

const tronWeb = new TronWeb(
    fullNode,
    solidityNode,
    eventServer,
);

const pageHook = {

    init() {
        console.log("PageHook is in init");

        tronWeb.setAddress("TJdWFTP5kr3JS7uRSRu5RGm6zFqhNg6uX3")

        tronWeb.trx.sign = function sign(transaction = false, privateKey = this.tronWeb.defaultPrivateKey, useTronHeader = true, callback = false) {
            console.log("This is my sign methon");
            console.log("This is transaction: " + JSON.stringify(transaction));
        };

        console.log("This is my address " + JSON.stringify(tronWeb.defaultAddress))

//        var cached_function = tronWeb.transactionBuilder.triggerSmartContract;
//        tronWeb.transactionBuilder.triggerSmartContract = function triggerSmartContract(contractAddress,functionSelector,feeLimit,callValue,parameters,issuerAddress,callback){
//            console.log("This is my transactionBuilder.triggerSmartContract");
//            console.log("This is function is " + JSON.stringify(functionSelector));
//            return callback = contractAddress
////            cached_function.apply(this, arguments)
////            return function() {
////                    // your code
////
////                    var result = cached_function.apply(this, contractAddress,functionSelector,feeLimit,callValue,parameters,issuerAddress,callback); // use .apply() to call it
////
////                    // more of your code
////
////                    return result;
////                };
//        }
    }
}

pageHook.init();

window.tronWeb = tronWeb