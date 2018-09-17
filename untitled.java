/**
     * 解析
     */

    public static SparseArray<BoxResponse> formatResponseCode(byte[] bytes) {
        // dbde 07 02 be   51 00     02 01 01    a56f0000000000000000

        String responseCode = JMLUtils.bytesToHexString(bytes);
        if (responseCode == null || responseCode.length() != 40 || !responseCode.toLowerCase().startsWith("dbde")) {
            return null;
        }
        responseCode = responseCode.toLowerCase();//
        // 去掉dbde
        responseCode = responseCode.substring(4);

        // 新建数组，每两个放一起
        List<Integer> array = new ArrayList<>();
        char[] chars = responseCode.toCharArray();
        for (int i = 0; i < chars.length / 2; i++) {

            Integer tmp;
            try {
                tmp = JMLUtils.getIntBy16("" + chars[i * 2] + chars[i * 2 + 1]);
            } catch (Exception e) {
//                e.printStackTrace();
                return null;
            }
            array.add(tmp);
        }

        // 应答数据长度
        int len1 = array.get(0);

        // 去掉长度 -- 上面的07
        array.remove(0);

        Integer _index, _be, _id, _success;

        // 判断是否是应答指令
        // 这边解析是否正确
        if (array.get(1) == BoxKey.HELM_NOTIFY_ID) {
            // db de 08 01 54 xxxxxxxx
            List<Integer> data1 = new ArrayList<>(array.subList(0, 2));
            array.subList(0, 2).clear(); // 删掉前2个
            len1 -= 2;
            _index = data1.get(0);
            _be = null;
            _id = data1.get(1);
            _success = 0;
        } else {
            // 序列号(值我发送的计数,每次不一样), be说明是应答指令, 51 应答命令ID, 00成功状态--成功
            List<Integer> data1 = new ArrayList<>(array.subList(0, 4));
            array.subList(0, 4).clear(); // 删掉前4个
            len1 -= 4;
            _index = data1.get(0);
            _be = data1.get(1);
            _id = data1.get(2);
            _success = data1.get(3);
        }

        if (_success != 0) {
            BleLog.d("_success" + _success + "");
            return null;
        }

        // 开始递归 取传的值 = 应答数据

        SparseArray<BoxResponse> _detailValue = new SparseArray<>();

        if (_id == BoxKey.BOX_SEND_ID || _id == BoxKey.HELM_NOTIFY_ID) {
            while (true) {
                int _len = array.get(0);
                if (len1 <= 0) {
                    break;
                } else {
                    // 长度
                    array.remove(0);

                    // type
                    int key = array.get(0);
                    array.remove(0);

                    _detailValue.put(key, new BoxResponse(_id, _index, key, new ArrayList<>(array.subList(0, _len - 1))));
                    array.subList(0, _len - 1).clear();
                }
                len1 -= (_len + 1);
            }
        } else {
            // 只有一条命令 -- 没有分指令的长度
            // key 为 _id
            BleLog.d(len1 + "");
            if (len1 >= 0 && array.size() >= len1) {
                _detailValue.put(0, new BoxResponse(_id, _index, 0, new ArrayList<>(array.subList(0, len1))));
            }
        }
        return _detailValue;
    }