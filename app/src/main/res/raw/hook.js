const TronWeb = require('tronweb')

// This provider is optional, you can just use a url for the nodes instead
const HttpProvider = TronWeb.providers.HttpProvider;
const fullNode = new HttpProvider('https://api.trongrid.io'); // Full node http endpoint
const solidityNode = new HttpProvider('https://api.trongrid.io'); // Solidity node http endpoint
const eventServer = new HttpProvider('https://api.trongrid.io'); // Contract events http endpoint

const privateKey = 'da146374a75310b9666e834ee4ad0866d6f4035967bfc76217c5a495fff9f0d0';

const tronWeb = new TronWeb(
    fullNode,
    solidityNode,
    eventServer,
    privateKey
);

const pageHook = {

    init() {
        console.log("PageHook is in init");

        tronWeb.trx.sign = function sign(transaction = false, privateKey = this.tronWeb.defaultPrivateKey, useTronHeader = true, callback = false) {
            console.log("This is my sign methon");
            console.log("This is transaction: " + JSON.stringify(transaction));
        };

        tronWeb.transactionBuilder.triggerSmartContract = (function triggerSmartContract(contractAddress,functionSelector,feeLimit,callValue,parameters,issuerAddress,callback){
            var cache_function = tronWeb.transactionBuilder.triggerSmartContract;


            return function() {
                    // your code
                                console.log("This is my transactionBuilder.triggerSmartContract");
                                console.log("This is function is " + JSON.stringify(functionSelector));

                    var result = cached_function.apply(contractAddress,functionSelector,feeLimit,callValue,parameters,issuerAddress,callback); // use .apply() to call it

                    // more of your code

                    return result;
                };
        })()
    }
}

pageHook.init();

window.tronWeb = tronWeb